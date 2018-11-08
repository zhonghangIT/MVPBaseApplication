package ${packageName};
import android.os.Bundle;
/**
 * @author zhonghang
 * description:
 */
public class ${activityClass} extends BaseActivity<${contractClass}.Presenter> implements ${contractClass}.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public ${contractClass}.Presenter initPresenter() {
        return new ${presenterClass}();
    }
    @Override
    public int getLayoutId() {
        return R.layout.${layoutName};
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }

}
