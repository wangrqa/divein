package com.lazytop.test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestDescartes {


  public static void main(String[] args) {
    Map<String, List<String>> groupData = new HashMap<>();
    List<String> colorList = Arrays.asList("红色", "黑色", "金色");
    groupData.put("color", colorList);
    List<String> sizeList = Arrays.asList("32G", "64G");
    groupData.put("size", sizeList);
    List<String> placeList = Arrays.asList("国产", "进口");
    groupData.put("place", placeList);
    List<String> portList = Arrays.asList("protA", "portB");
    groupData.put("port", portList);
    List<Map<String, String>> result = descartes(groupData);
    System.out.println(result.size());
    for (Map<String, String> record : result) {
      System.out.println(record);
    }



    List<List<String>> xx = descartesNoKey(Arrays.asList(colorList, placeList));

    System.out.println(xx);

    xx = descartesNoKey(Arrays.asList(colorList, placeList, portList));

    System.out.println(xx);

  }


  public static <T> List<Map<String, T>> descartes(Map<String, List<T>> groupData) {
    List<Map<String, T>> resultList = new ArrayList<>();
    for (String group : groupData.keySet()) {
      if (resultList.isEmpty()) {
        List<T> firstLoopGroupDocs = groupData.get(group);
        for (T firstLoopGroupDoc : firstLoopGroupDocs) {
          Map<String, T> temp = new HashMap<>();
          temp.put(group, firstLoopGroupDoc);
          resultList.add(temp);
        }
      } else {

        resultList = resultList.stream().flatMap(
          item ->
          groupData.get(group).stream().map(
            item2 -> {
              Map<String, T> tempMap = new HashMap<>();
              tempMap.putAll(item);
              tempMap.put(group, item2);
              return tempMap;
            })
      ).collect(Collectors.toList());
      }
    }
    return resultList;
  }




  public static <T> List<List<String>> descartesNoKey(List<List<String>> groupData) {
    List<List<String>> resultList = new ArrayList<>();
    for (List<String> group : groupData) {
      if (resultList.isEmpty()) {
        for (String firstLoopGroupDoc : group) {
          List<String> temp = new ArrayList<>();
          temp.add(firstLoopGroupDoc);
          resultList.add(temp);
        }
      } else {
        resultList = resultList.stream().flatMap(
          item ->
            group.stream().map(
              item2 -> {
                List<String> tempMap = new ArrayList<>();
                tempMap.addAll(item);
                tempMap.add(item2);
                return tempMap;
              })
        ).collect(Collectors.toList());
      }
    }
    return resultList;
  }





}
