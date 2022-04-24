package com.example.tripplanner;

import android.util.Log;

import com.example.tripplanner.Models.DataObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class XmlPullParserHandler {
    private final List<DataObject> dataItemsList = new ArrayList<DataObject>();
    private DataObject item;
    private String text;

    public List<DataObject> parse(InputStream is) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);
            int eventType = parser.getEventType();
            boolean isStarted = true;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                Integer statusAdded = 0;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("item")) {

                            item = new DataObject();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG: {
                        if (tagname.equalsIgnoreCase("item")) {
                            dataItemsList.add(item);
                        } else if (tagname.equalsIgnoreCase("category")) {
                            item.setStatus(text);
                        } else if (tagname.equalsIgnoreCase("description")) {
                            if (isStarted) {
                                item = new DataObject();
                                isStarted = false;
                            }
                            item.setDescription(text);
                        } else if (tagname.equalsIgnoreCase("guid")) {
                            item.setGuid(text);
                        } else if (tagname.equalsIgnoreCase("link")) {
                            item.setLink(text);
                        } else if (tagname.equalsIgnoreCase("pubDate")) {
                            item.setPublishDate(text);
                        } else if (tagname.equalsIgnoreCase("title")) {
                            item.setTitle(text);
                        } else if (tagname.equalsIgnoreCase("reference")) {
                            item.setReference(text);
                        } else if (tagname.equalsIgnoreCase("road")) {
                            item.setRoad(text);
                        } else if (tagname.equalsIgnoreCase("region")) {
                            item.setRegion(text);
                        } else if (tagname.equalsIgnoreCase("county")) {
                            item.setCounty(text);
                        } else if (tagname.equalsIgnoreCase("latitude")) {
                            item.setLatitude(text);
                        } else if (tagname.equalsIgnoreCase("longitude")) {
                            item.setLongitude(text);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Log.d("dataITem", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("dataITem", e.toString());
        }

        Log.d("DataList size", dataItemsList.size() + "");
        return dataItemsList;
    }
}