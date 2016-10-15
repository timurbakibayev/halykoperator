package kz.sagrad.halykoperator;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class Menu {
    public static String TAG = "Menu.java";
    public static Map<String,String> menuMap;
    public static ArrayList<MenuElement> menuArrayList = new ArrayList<MenuElement>();
    public static String currentMenu = "";

    public static void loadMenu() {
        HalykOperator.ref.child("menu").addValueEventListener(new ValueEventListener() {
                          @Override
                          public void onDataChange(DataSnapshot dataSnapshot) {
                              menuMap = (Map<String,String>)dataSnapshot.getValue();
                              constructArrayList();
                          }

                          @Override
                          public void onCancelled(FirebaseError firebaseError) {

                          }
                      });
    }

    private static void constructArrayList() {
        menuArrayList = new ArrayList<MenuElement>();
        for (Map.Entry<String, String> entry : menuMap.entrySet()) {
            String id = entry.getKey();
            String text = entry.getValue();
            String parent = "";
            Boolean isAParent = false;
            for (Map.Entry<String, String> entry1 : menuMap.entrySet()) {
                if ((entry1.getKey().length() < id.length()) && //should be a parent, not a child
                     (entry1.getKey().length() > parent.length()) && //finding maximum
                         (id.substring(0,entry1.getKey().length()).equals(entry1.getKey())) //prefix
                ) {
                    parent = entry1.getKey();
                }
                if ((entry1.getKey().length() > id.length()) && //is a child
                        (entry1.getKey().substring(0,id.length()).equals(id)) //vice versa prefix
                        ) {
                    isAParent = true;
                }
            }
            menuArrayList.add(new MenuElement(parent, id, text, isAParent));
        }
        Collections.sort(menuArrayList, new Comparator<MenuElement>() {
            @Override
            public int compare(MenuElement o1, MenuElement o2) {
                return o1.id.compareTo(o2.id);
            }
        });
        ArrayList<MenuElement> menuNodes = new ArrayList<MenuElement>();
        for (MenuElement menuElement : menuArrayList) {
            //System.out.println(menuElement.parent + ",    " + menuElement.id + ",    " + menuElement.text);
            if (!menuElement.folder)
                menuNodes.add(menuElement);
        }
        Log.w(TAG, "constructArrayList: MENU READY");
        System.out.println("MENU READY");
    }

}
