package com.zerowater.environment.ui.main.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zerowater.environment.R;
import com.zerowater.environment.databinding.FragmentHistoryBinding;
import com.zerowater.environment.databinding.ItemHistoryProductBinding;
import com.zerowater.environment.ui._base.BaseDaggerFragment;
import com.zerowater.environment.ui._base.BaseDataBindingHolder;
import com.zerowater.environment.ui._base.BaseViewModelAdapter;
import com.zerowater.environment.util.ListUtil;

import java.util.List;

import androidx.annotation.NonNull;
import timber.log.Timber;


/**
 * Created by YoungSoo Kim on 2019-09-26.
 * Zero Ltd
 * byzerowater@gmail.com
 * HistoryFragment
 * 내역
 */
public class HistoryFragment extends BaseDaggerFragment<FragmentHistoryBinding, HistoryViewModel> {

    /**
     * HistoryFragment Instance 생성
     *
     * @return HistoryFragment
     */
    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_history;
    }

    @Override
    protected Class getModelClass() {
        return HistoryViewModel.class;
    }

    @Override
    protected void onActivityCreated(FragmentHistoryBinding viewDataBinding, HistoryViewModel viewModel) {

        viewDataBinding.setViewModel(viewModel);
        viewDataBinding.setRecyclerViewAdapter(new ProductAdapter(viewModel));

        viewModel.getResponse().observe(this, resource -> {
            switch (resource.getStatus()) {
                case SUCCESS:
                    List<String> data = resource.getData();
                    if (!ListUtil.isEmpty(data)) {
                        viewDataBinding.setList(data);
                    }
                    break;
                case ERROR:
                    break;
            }
        });

        viewModel.getList();

        Timber.i("onActivityCreated");
    }


    /**
     * 상품 어답터
     */
    public static class ProductAdapter extends BaseViewModelAdapter<String, HistoryViewModel, BaseDataBindingHolder> {
        /**
         * 상품 어답터
         *
         * @param viewModel HistoryViewModel
         */
        public ProductAdapter(HistoryViewModel viewModel) {
            super(viewModel);
        }

        @Override
        public BaseDataBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ProductHolder(ItemHistoryProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull BaseDataBindingHolder holder, int position) {
            holder.bind(position, getData().get(position));
        }

        @Override
        public int getItemCount() {
            return ListUtil.getListCount(getData());
        }

        /**
         * 상품 홀더
         */
        class ProductHolder extends BaseDataBindingHolder<ItemHistoryProductBinding, String> {

            /**
             * 상품 홀더
             *
             * @param binding ItemHistoryProductBinding
             */
            public ProductHolder(ItemHistoryProductBinding binding) {
                super(binding);
            }

            @Override
            public void bind(int position, String title) {
                getViewDataBinding().setTitle(title);
                getViewDataBinding().setPosition(position);
            }
        }

    }
}
