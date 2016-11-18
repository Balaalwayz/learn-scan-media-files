package com.test.control;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DirectoryScannerMain {
	
	static Map<String, String> allowedFileTypeMap = new HashMap<>();
	
	static {
		checkAllowedfiles();
	}


	Map<String, List<String>> fileListMap = new HashMap<>();
	
	List<String> fileNameList;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DirectoryScannerMain directoryScannerMain = new DirectoryScannerMain();
		//Collections.sort(new ArrayList());
		String directoryPath = directoryScannerMain.getUserInput();
		
		
		directoryScannerMain.processDirectoryIteratively(directoryPath);

		System.out.println("The file extension are " + directoryScannerMain.listKeys());
		//E:\Entertainment
		//System.out.println(fileListMap);
		directoryScannerMain.listMapContent();

	}

	private String getUserInput() {

		System.out.println("Enter the Directory ");
		Scanner scanner = new Scanner(System.in);

		String inputDirName = scanner.next();
		// System.out.println("The Entered Input Directory is " + inputDirName);

		return inputDirName;
		
	}

	private Set<String> listKeys() {
		// TODO Auto-generated method stub
		Set<String> fileExtension = fileListMap.keySet();
		return fileExtension;
	}

	private void processDirectoryIteratively(String fileNameStr) {
		/*
		 * //for(String subDir : file.list()) { System.out.println(fileNameStr);
		 * File file = new File(fileNameStr);
		 * 
		 * //System.out.println(file.getAbsolutePath());
		 * 
		 * //List the Subdirectories String[] subDirListStr = file.list();
		 * 
		 * for (String subDirFileStr:subDirListStr) {
		 * //System.out.println(subDirFileStr); File subDirFile = new
		 * File(fileNameStr+"\\"+subDirFileStr);
		 * 
		 * System.out.println(subDirFile.getAbsolutePath());
		 * if(subDirFile.isFile()) { addToList(subDirFile); } else if
		 * (subDirFile.isDirectory()) { return
		 * recursiveIterDir(fileNameStr+"\\"+subDirFileStr); }else {
		 * System.out.println("all false"); } }E:\Entertainment
		 * System.out.println("Return"+fileNameStr); //}
		 * 
		 * return fileNameStr;
		 */
		// System.out.println("FileName " + fileNameStr);
		File file = new File(fileNameStr);
		if (file.isDirectory()) {
			String[] subDirListStr = file.list();

			for (String subDirFileStr : subDirListStr) {
				// System.out.println(subDirFileStr);
				File subDirFile = new File(fileNameStr + "\\" + subDirFileStr);
				// System.out.println("AbsoluePath " +
				// subDirFile.getAbsolutePath());
				processDirectoryIteratively(subDirFile.getAbsolutePath());
			}
		} else {
			// System.out.println("File is Found " + file);
			addToList(file);
		}

	}

	private void addToList(File filename) {
		// The.Gladiator.avi
		// Anniyan.mkv
		// The.War.of.the.worlds.DAT
		String[] fileNameParts = filename.getName().split("\\.");
		
		int size = fileNameParts.length;
		
		String mapKey = fileNameParts[size - 1];
		
		String filetypeKey = allowedFileTypeMap.get(mapKey.toUpperCase());

		if (filetypeKey != null) {
			
			String freeSpace = Long.valueOf(filename.getFreeSpace()).toString();
			String totalSpace = Long.valueOf(filename.getTotalSpace()).toString();
			String space = Long.valueOf(filename.getUsableSpace()).toString();
			//String fileNameStr = filename.getName() +"|"+freeSpace+"|"+totalSpace+"|"+space;
			String fileNameStr = filename.getName() +"|"+filename.getParent() +"|"+filename.length()/1048576+"MB";
			if (fileListMap.containsKey(filetypeKey)) {
				/**
				 * [avi-[{Anniyan.avi},{Gladiator.avi},{}]]
				 */
				
				fileListMap.get(filetypeKey).add(fileNameStr);

			} else {
				fileNameList = new ArrayList<String>();
				fileNameList.add(fileNameStr);
				fileListMap.put(filetypeKey, fileNameList);

			}
			

		}

	}
	
	private static void checkAllowedfiles() {
		for (FILETYPES fileType : FILETYPES.values()) {
			/*if(mapKey.equalsIgnoreCase(fileType.name())) {
				allowedType = true;
			}*/
			allowedFileTypeMap.put(fileType.name(), fileType.name());
		}
	}

	// Define the allowable file types in the enum
	public enum FILETYPES{
		AVI("Avi"),
		DAT("Dat"),
		MKV("Mkv"),
		MP3("Mp3"),
		MP4("Mp4"),
		SRT("SRT");
		
		public String fileType;
		
		FILETYPES(String fileType) {
			this.fileType = fileType;
		}
		
		public String getFileType() {
			return fileType;
		}
	}
		
	
	private void listMapContent() {
		
		Set<Map.Entry<String, List<String>>> entries = fileListMap.entrySet();
		
		for (Map.Entry<String, List<String>> entry: entries) {
			
			System.out.println(entry.getKey());
			List<String> sortedList = entry.getValue();
					Collections.sort(sortedList);
			
			for (String fileName : sortedList) {
				
				System.out.println(fileName);
			}
		}
	}
}
