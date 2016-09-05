package com.example.mangafinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TextView.*;
import android.support.v4.app.Fragment;
import android.graphics.*;

public class SucheFragment extends Fragment {
	private Button _updateBtn;
	private View _view;
	private TableLayout _table;
	private SearchView _searchView;

	private Vector<Buch> _buchs = new Vector<Buch>();

	private void updateTable() {
		_table = (TableLayout) _view.findViewById(R.id.table);

		_table.setBackgroundColor(Color.YELLOW);

		_table.removeAllViews();

		String regex = _searchView.getQuery().toString();

		if (regex.isEmpty()) regex = ".*";

		/*Buch buch = new Buch();

		buch.setPagesCount(1);
		buch.setGenre("abc");
		buch.setTitle("def");

		TableRow row = new TableRow(_view.getContext());

		//TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
		row.setBackgroundColor(Color.RED);
		row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

		TextView nameView = new TextView(_view.getContext());
		TextView pagesView = new TextView(_view.getContext());
		TextView genreView = new TextView(_view.getContext());

		nameView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
		pagesView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
		genreView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

		nameView.setText(buch.getTitle());
		pagesView.setText(Integer.toString(buch.getPagesCount()));
		genreView.setText(buch.getGenre());

		row.addView(nameView);
		row.addView(genreView);
		row.addView(pagesView);

		_table.addView(row);*/

		for (Buch buch : _buchs) {
			if (_table.getChildCount() >= 5) break;
			if (!buch.getTitle().matches(regex)) continue;

			TableRow row = new TableRow(_view.getContext());

			//TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
			row.setBackgroundColor(Color.RED);
			row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

			TextView nameView = new TextView(_view.getContext());
			TextView pagesView = new TextView(_view.getContext());
			TextView genreView = new TextView(_view.getContext());

			nameView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
			pagesView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
			genreView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

			nameView.setText(buch.getTitle());
			pagesView.setText(Integer.toString(buch.getPagesCount()));
			genreView.setText(buch.getGenre());

			row.addView(nameView);
			row.addView(genreView);
			row.addView(pagesView);

			_table.addView(row);
		}
	}

	private void addBuch(Buch buch) {
		if (_buchs.contains(buch)) return;

		_buchs.add(buch);

		updateTable();
	}

	private String parseBox(String input, String regex) {
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	private class aktualisierenAsyncTask extends AsyncTask<Void, Void, Vector<Buch>> {
		@Override
		protected Vector<Buch> doInBackground(Void... params) {
    		try {

				Document overviewDocRaw = Jsoup.connect("http://www.tokyopop.de/manga-shop/index.php?cPath=869").get();

				Document overviewDoc = Jsoup.parse(overviewDocRaw.toString());

				Vector<String> links = new Vector<String>();

				for (Element el : overviewDoc.getAllElements()) {
					if (el.className().equals("cat_listing_box")) {
						el = el.getElementsByTag("a").first();

						String link = el.attr("href");

						links.add(link);
						Log.e("link", link);
					}
				}

				Vector<Buch> buchs = new Vector<Buch>();

				for (String link : links) {
					Log.e("search", link);
					Document docRaw = Jsoup.connect(link).get();

					Document doc = Jsoup.parse(docRaw.toString());

					Vector<Element> boxes = new Vector<Element>();

					for (Element el : doc.getAllElements()) {
						if (el.className().equals("product_listing_text")) {
							boxes.add(el);
						}
					}

					for (Element box : boxes) {
						String content = box.ownText();

						Element el = box.getElementsByTag("strong").first();
						el = el.getElementsByTag("a").first();

						String title = el.ownText();

						String pagesCount = parseBox(content, "Seitenzahl: (\\d+)");
						String genre = parseBox(content, "Genre: (\\w+)");

						//textView.setText(String.format("Pages: %s%sGenre: %s", pagesCount, File.pathSeparator, genre));

						Buch buch = new Buch();

						buch.setTitle(title);
						try {
							buch.setPagesCount(Integer.parseInt(pagesCount));
						} catch (NumberFormatException e) {
							buch.setPagesCount(0);
						}
						buch.setGenre(genre);

						buchs.add(buch);
					}
				}

				return buchs;
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
				Log.e("excp", e.getMessage());
    		}
    		
    		return null;
		}

		@Override
		protected void onPostExecute(Vector<Buch> buchs) {
			for (Buch buch : buchs) {
				addBuch(buch);
			}
		}
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	
    	_view = inflater.inflate(R.layout.suche, container, false);
    	
    	_updateBtn = (Button) _view.findViewById(R.id.aktualisierenBtn);
    	
    	_updateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
	        	Log.d("suchefragment", "aktualisieren");

	        	new aktualisierenAsyncTask().execute();
			}

    	});

		_searchView = (SearchView) _view.findViewById(R.id.searchView);

		_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				Log.d("query", "update");

				updateTable();

				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

        return _view;
    }
}
