package com.wysengine.fishing.export;

public class App {
  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      System.out.println("commends:");
    }
    String command = args[0];

    String[] subArgs = new String[args.length - 1];
    System.arraycopy(args, 1, subArgs, 0, args.length - 1);
    if (command.equals("ExportHBaseTableToParquet")) {
      ExportHBaseTableToParquet.main(subArgs);
    } else if (command.equals("ParquetReader")) {
      ParquetReader.main(subArgs);
    } else {
      System.out.println("Involve command:" + command);
    }
  }
}
