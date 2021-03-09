package com.eomcs.pms.table;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AbstractJsonDataTable<T> {

  File jsonFile;
  List<T> list;
  Class<T> elementType;

  public AbstractJsonDataTable(File file, Class<T> elementType) {
    this.jsonFile = file;
  }

  protected void loadJsonData() {

    try (BufferedReader in = new BufferedReader(new FileReader(jsonFile))) {

      StringBuilder strBuilder = new StringBuilder();
      String str = null;
      while ((str = in.readLine()) != null) {
        strBuilder.append(str);
      }

      Type listType = TypeToken.getParameterized(ArrayList.class, elementType).getType();
      this.list = new Gson().fromJson(strBuilder.toString(), listType);
      System.out.printf("%s 파일 데이터 로딩!\n", jsonFile.getName());

    } catch (Exception e) {
      System.out.printf("%s 파일 데이터 로딩 중 오류 발생!\n", jsonFile.getName());
      this.list = new ArrayList<T>();
    }
  }

  protected void saveJsonData() {
    try (BufferedWriter out = new BufferedWriter(new FileWriter(jsonFile))) {
      out.write(new Gson().toJson(list));
      System.out.printf("파일 %s 데이터 저장!\n", jsonFile.getName());

    } catch (Exception e) {
      System.out.printf("파일 %s에 데이터를 저장하는 중에 오류 발생!\n", jsonFile.getName());
    }
  }
}
