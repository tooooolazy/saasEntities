package com.tooooolazy.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONObject;

public class Importer {

	public static <T> List<T> getEntities(File file, Class cl) {
		List<T> list = new ArrayList<T>();

		try {
			Workbook workbook = WorkbookFactory.create(new FileInputStream(file) );
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	public static List getEntities(InputStream in, String tab, int minCol, int maxCol, String[] columnNames) {
		List list = new ArrayList();

		try {
			Workbook workbook = WorkbookFactory.create(in);
			Sheet sheet = workbook.getSheet(tab);

			for (int i=sheet.getFirstRowNum(); i<=sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					JSONArray ja = new JSONArray();
					for (int j=minCol; j<=maxCol; j++) {
						Cell cell = row.getCell(j);
		            	if(cell != null) {
		            		String cellText = cell.toString();
		            		System.out.println(columnNames[j] + ": " + cellText);
		            		ja.put(cellText);
		            	}
					}
					list.add(ja);
				}
			}

		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
}
