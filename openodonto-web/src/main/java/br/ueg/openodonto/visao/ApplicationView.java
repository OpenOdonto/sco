package br.ueg.openodonto.visao;

import java.io.Serializable;
import java.util.Map;

public interface ApplicationView extends Serializable{

    void showOut();

    void showAction();

    void showPopUp(String msg);

    String getPopUpMsg();
    
    public Map<String,String> getProperties();

    boolean getDisplayMessages();

    boolean getDisplayPopUp();

    void addResourceMessage(String key, String target);

    void addResourceDynamicMenssage(String msg, String target);

    void addLocalMessage(String key, String target, boolean targetParam);

    void addLocalDynamicMenssage(String msg, String target, boolean targetParam);
    
    String getMessageFromResource(String name);

    void refresh();

}
