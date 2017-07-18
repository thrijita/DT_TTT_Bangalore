package com.niit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class FileHandler {

	QueryParser queryparser = new QueryParser();
	public String alldata[], str, finaldata[], withoutheader[], conditions[], hdr, headerdata[], columnorder,
			columnsreceived, columns[], query, relationalquery;
	public String filename;
	private ColumnNames columnamess = new ColumnNames();
	public Map<String, Integer> headermap = new HashMap<>();
	public Map<Integer, String> rowdatamap = new TreeMap<>();
	public List<RelationalConditions> relationalList = new ArrayList<>();
	public Map<Integer,String> rowwiseData=new HashMap<>();
	////// ---Fetching of data from the csv file and trying to put the row data
	////// in an array----///

	public String[] fetchingRowData(String filename, String query, String relationalquery, List relationallist)
			throws Exception {
		this.filename = filename;
		this.query = query;
		this.relationalquery = relationalquery;
		this.relationalList = relationallist;
		String str;
		FileReader f = new FileReader("E:\\" + filename);
		BufferedReader br = new BufferedReader(f);
		List<String> list = new ArrayList<String>();
		while ((str = br.readLine()) != null) {

			list.add(str);
		}
		String[] stringArr = list.toArray(new String[0]);

		finaldata = Arrays.copyOf(stringArr, stringArr.length - 1);

		this.gettingHeaderMap();
		this.gettingRowDataMap();
		this.columnSeparator();
		this.checkingHeaderInColumn(query);
		this.ColumnDataProcessor(columns, rowdatamap, headermap);
		this.whereQueryProcessing();
		return finaldata;
	}

	public String getColumnorder() {
		return columnorder;
	}

	public void setColumnorder(String columnorder) {
		this.columnorder = columnorder;
	}

	///// ----storing the splitted first header into a map of header name and
	///// indexpoint----////
	public Map<String, Integer> gettingHeaderMap() {
		headerdata = finaldata[0].split(",");

		int length = headerdata.length;
		for (int i = 0; i < length; i++) {
			headermap.put(headerdata[i].replaceAll("^\"|\"$", ""), i);
		}

		return headermap;
	}

	////// ----storing the splitted data i.e., each row into a map with their
	////// row number----/////

	public Map<Integer, String> gettingRowDataMap() {

		int length = finaldata.length;
		for (int i = 0; i < length; i++) {
			rowdatamap.put(i, finaldata[i]);
		}

		return rowdatamap;
	}

	//// ---Separated column names in the select query---//////
	public String[] columnSeparator() {
		String[] separateddata = query.split(" ");
		columnsreceived = separateddata[1].trim();
		columns = columnsreceived.split(",");
		return columns;

	}

	///// ---- displaying of all the column based data----////
	public FileHandler checkingHeaderInColumn(String query) {

		String[] separateddata = query.split(" ");
		columnsreceived = separateddata[1].trim();
		columns = columnsreceived.split(",");
		int position;
		if (columns.length == 1 || columns.equals("*")) {
//
//			for (String string : finaldata) {
//				System.out.println(string.replace("\"", "").replace(",", " "));
//			}
		} else {
			Set entrySet = headermap.entrySet();

			Iterator it = entrySet.iterator();

			while (it.hasNext()) {
				for (int i = 0; i < columns.length; i++) {
					Map.Entry me = (Map.Entry) it.next();

					String value = (String) me.getKey();
					if (value.contains(columns[i])) {
						position = (int) me.getValue();

						for (int s = 0; s < finaldata.length; s++) {
							String[] temp = finaldata[s].split(",");
							// System.out.println(temp[position].replaceAll("^\"|\"$",
							// ""));

						}

					}

				}

			}
		}
		return this;
	}

	//// -------------column where conditioning---//////////////
	public Map<Integer, String> ColumnDataProcessor(String[] columns, Map<Integer, String> rowdata,
			Map<String, Integer> header) {
		Map<Integer, String> newMap = new HashMap<>();
		int lengthcount = columns.length;
		List<String> databeforeparsing = new ArrayList<>();
		int indexofhead[] = new int[lengthcount];
		if (columns[0].contains("*")) {
			this.checkingHeaderInColumn(query);
		} else {

			int definedlength = headerdata.length;

			for (int i = 0; i < lengthcount; i++) {
				for (int j = 0; j < definedlength; j++) {
					if (headerdata[j].contains(columns[i])) {
						indexofhead[i] = j;

					}
				}

			}

			for (String in : rowdata.values()) {
				databeforeparsing.add(in);
			}

//			for (Integer i : indexofhead) {
//				System.out.println(i);
//			}
			int rowdatalength = databeforeparsing.size();

			String storedata[] = new String[rowdatalength];

			for (int r = 0; r < rowdatalength; r++) {
				storedata[r] = databeforeparsing.get(r);
				String datasftersplit[] = storedata[r].split(",");

				StringBuffer stringbuffer = null;
				stringbuffer = new StringBuffer();
				for (int k = 0; k < lengthcount; k++) {
					stringbuffer.append(datasftersplit[indexofhead[k]] + ",");
				}
				newMap.put(r, stringbuffer.toString());
			}

		}
		return newMap;

	}
//code starts for where fetching data
	public Map<Integer, String> whereQueryProcessing()throws IOException {
		List<String> middlelist = new ArrayList<>();
		List<String> resultantlist = new ArrayList<>();

		BufferedReader bufferedreader = null;
		String columnname = "", values = "", operator = "";
		int lengthcount = columns.length;
		int[] indexofhead = new int[lengthcount];
		int indexcount = 0, columncount = 0, temp = 0, headerposition = 0;
		String string = "";
		int definedlength = headerdata.length;
		try {
			Iterator iterator = relationalList.iterator();
			while (iterator.hasNext()) {
				RelationalConditions rc = (RelationalConditions) iterator.next();
				columnname = rc.getColumn();

				for (int count = 0; count < definedlength; count++) {
					String hdr = headerdata[count].replaceAll("^\"|\"$", "");
					if (hdr.contains(columnname)) {

						headerposition = count;
						
					}
				}
				values = rc.getValue();
				operator = rc.getOperator();
				
			}
			bufferedreader = new BufferedReader(new FileReader("E:\\" + filename));
			columncount = columns.length;
			string = bufferedreader.readLine();
			string = bufferedreader.readLine();
			for (int i = 0; i < lengthcount; i++) {
				for (int j = 0; j < definedlength; j++) {
					if (headerdata[j].contains(columns[i])) {
						indexofhead[i] = j;

					}
				}

			}
			if (columns[0].equals("*")) {
				while (string != null) {
					if (operator.equals("="))
					{
						String[] afterdatasplit = string.split(",");

						if (afterdatasplit[headerposition].equals(values)) {
							rowwiseData.put(indexcount, string);
							temp++;
						}
						indexcount++;
						string = bufferedreader.readLine();
					}
				}
				//System.out.println(rowwisedata);
				return rowwiseData;
			} else {
				while (string != null) {
					String afterdatasplit[] = string.split(",");
					for (int i = 0; i < lengthcount; i++) {
						for (int j = 0; j < definedlength; j++) {
							if (headerdata[j].contains(columns[i])) {
								indexofhead[i] = j;

							}
						}

					}
					if (operator.equals("=")) {
						long residence = Math.max(Long.parseLong(afterdatasplit[headerposition].replaceAll("^\"|\"$", "")),
								Long.parseLong(values));
								if (Long.parseLong(afterdatasplit[headerposition].replaceAll("^\"|\"$", "")) == Long.parseLong(values)) {
							StringBuffer stringbuffer = new StringBuffer();

							for (int k = 0; k < lengthcount; k++) {
								for (temp = 0; temp < columncount - 1; temp++) {
									stringbuffer.append(afterdatasplit[indexofhead[k]] + ",");
									String stringg = stringbuffer.toString();

									rowwiseData.put(indexcount, stringg.substring(0, stringg.length() - 1));

								}

							}

						}
						indexcount++;
						string = bufferedreader.readLine();
					}

					else if (operator.equals(">")) {
						long residence = Math.max(
								Long.parseLong(afterdatasplit[headerposition].replaceAll("^\"|\"$", "")),
								Long.parseLong(values));
						if (Long.parseLong(
								afterdatasplit[headerposition].replaceAll("^\"|\"$", "")) > (Long.parseLong(values))) {
							StringBuffer stringbuffer = new StringBuffer();

							for (int k = 0; k < lengthcount; k++) {
								for (temp = 0; temp < columncount - 1; temp++) {
									stringbuffer.append(afterdatasplit[indexofhead[k]] + ",");
									String stringg = stringbuffer.toString();

									rowwiseData.put(indexcount, stringg.substring(0, stringg.length() - 1));

								}

							}

						}
						indexcount++;
						string = bufferedreader.readLine();

					}

					else if (operator.equals(">=")) {
						long residence = Math.max(
								Long.parseLong(afterdatasplit[headerposition].replaceAll("^\"|\"$", "")),
								Long.parseLong(values));
						if (Long.parseLong(
								afterdatasplit[headerposition].replaceAll("^\"|\"$", "")) >= (Long.parseLong(values))) {
							StringBuffer stringbuffer = new StringBuffer();

							for (int k = 0; k < lengthcount; k++) {
								for (temp = 0; temp < columncount - 1; temp++) {
									stringbuffer.append(afterdatasplit[indexofhead[k]] + ",");
									String stringg = stringbuffer.toString();

									rowwiseData.put(indexcount, stringg.substring(0, stringg.length() - 1));

								}

							}

						}
						indexcount++;
						string = bufferedreader.readLine();
					} else if (operator.equals("<")) {
						long residence = Math.max(
								Long.parseLong(afterdatasplit[headerposition].replaceAll("^\"|\"$", "")),
								Long.parseLong(values));
						if (Long.parseLong(
								afterdatasplit[headerposition].replaceAll("^\"|\"$", "")) < (Long.parseLong(values))) {
							StringBuffer stringbuffer = new StringBuffer();

							for (int k = 0; k < lengthcount; k++) {
								for (temp = 0; temp < columncount - 1; temp++) {
									stringbuffer.append(afterdatasplit[indexofhead[k]] + ",");
									String stringg = stringbuffer.toString();

									rowwiseData.put(indexcount, stringg.substring(0, stringg.length() - 1));

								}

							}

						}
						indexcount++;
						string = bufferedreader.readLine();
					} else if (operator.equals("<=")) {
						long residence = Math.max(
								Long.parseLong(afterdatasplit[headerposition].replaceAll("^\"|\"$", "")),
								Long.parseLong(values));
						if (Long.parseLong(
								afterdatasplit[headerposition].replaceAll("^\"|\"$", "")) <= (Long.parseLong(values))) {
							StringBuffer stringbuffer = new StringBuffer();

							for (int k = 0; k < lengthcount; k++) {
								for (temp = 0; temp < columncount - 1; temp++) {
									stringbuffer.append(afterdatasplit[indexofhead[k]] + ",");
									String stringg = stringbuffer.toString();

									rowwiseData.put(indexcount, stringg.substring(0, stringg.length() - 1));

								}

							}

						}
						indexcount++;
						string = bufferedreader.readLine();
					}
					else if (operator.equals("!=")) {
						long residence = Math.max(
								Long.parseLong(afterdatasplit[headerposition].replaceAll("^\"|\"$", "")),
								Long.parseLong(values));
						if (Long.parseLong(afterdatasplit[headerposition].replaceAll("^\"|\"$", "")) != (Long
								.parseLong(values))) {
							StringBuffer stringbuffer = new StringBuffer();

							for (int k = 0; k < lengthcount; k++) {
								for (temp = 0; temp < columncount - 1; temp++) {
									stringbuffer.append(afterdatasplit[indexofhead[k]] + ",");
									String stringg = stringbuffer.toString();

									rowwiseData.put(indexcount, stringg.substring(0, stringg.length() - 1));

								}

							}

						}
						indexcount++;
						string = bufferedreader.readLine();
					}

				}
			}

			List<String> rowiselist = new ArrayList<>(rowwiseData.values());
			rowwiseData.clear();
			if (middlelist.isEmpty()) {
				Collections.copy(middlelist, rowiselist);
				rowiselist.clear();
			} else {

				Iterator iteratorlist = queryparser.logicalOperator.iterator();
				while (iteratorlist.hasNext()) {
					if (((String) iteratorlist.next()).contains("and")) {
						resultantlist = this.intersection(middlelist, rowiselist);
						middlelist.clear();
						rowiselist.clear();
						Collections.copy(middlelist, resultantlist);
					} else if (((String) iteratorlist.next()).contains("or")) {
						resultantlist = this.union(middlelist, rowiselist);
						middlelist.clear();
						rowiselist.clear();
						Collections.copy(middlelist, resultantlist);

					}

				}

			}

			Iterator listtomap = resultantlist.iterator();
			while (listtomap.hasNext()) {
				for (int i = 0; i < resultantlist.size(); i++) {
					rowwiseData.put(i, (String) listtomap.next());
				}

				}
			

		} catch (Exception e) {
			// redirect them to string columns;
		}
//System.out.println(rowwisedata);
		return rowwiseData;
	}
public <T> List<T> union(List<T> list1, List<T> list2) {
    Set<T> set = new HashSet<T>();

    set.addAll(list1);
    set.addAll(list2);

    return new ArrayList<T>(set);
}

public <T> List<T> intersection(List<T> list1, List<T> list2) {
    List<T> list = new ArrayList<T>();

    for (T t : list1) {
        if(list2.contains(t)) {
            list.add(t);
        }
    }

    return list;
}

}