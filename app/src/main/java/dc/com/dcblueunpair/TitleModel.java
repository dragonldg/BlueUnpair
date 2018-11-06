package dc.com.dcblueunpair;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class TitleModel extends BaseObservable {
    private String title;

    TitleModel(String title) {
        this.title = title;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
        notifyPropertyChanged(BR.canClick);
    }

    @Bindable
    public boolean isCanClick() {
        return !title.contains("没有配对");
    }
}
