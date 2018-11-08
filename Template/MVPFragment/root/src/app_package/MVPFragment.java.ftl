package ${packageName};

import android.os.Bundle;
import android.view.View;

/**
 * @author zhonghang
 * description:
 */
public class ${className} extends BaseFragment<${contractClass}.Presenter> implements ${contractClass}.View {

    /**
     * 创建一个静态方法用于创建fragment的对象
     * 主要是在参数列表中传递初始化fragment需要的数据
     * @return 一个该对象的新的实例
     */
    public static ${className} newInstance() {
        ${className} fragment = new ${className}();
        Bundle args = new Bundle();
        //创建Fragment需要传递的参数
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           //创建时传递进来的参数，在此处能够取到
        }
    }

    @Override
    public void initViews(View parentView) {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.${fragmentName};
    }
   @Override
    public ${contractClass}.Presenter initPresenter() {
        return new ${presenterClass}();
    }
}
