package com.zerowater.environment.ui.main.board;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zerowater.environment.R;
import com.zerowater.environment.databinding.FragmentBoardBinding;
import com.zerowater.environment.databinding.ItemBoardProductBinding;
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
 * BoardFragment
 */
public class BoardFragment extends BaseDaggerFragment<FragmentBoardBinding, BoardViewModel> {

    /**
     * BoardFragment Instance 생성
     *
     * @return BoardFragment
     */
    public static BoardFragment newInstance() {
        BoardFragment fragment = new BoardFragment();
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_board;
    }

    @Override
    protected Class getModelClass() {
        return BoardViewModel.class;
    }

    @Override
    protected void onActivityCreated(FragmentBoardBinding viewDataBinding, BoardViewModel viewModel) {

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
    public static class ProductAdapter extends BaseViewModelAdapter<String, BoardViewModel, BaseDataBindingHolder> {
        /**
         * 상품 어답터
         *
         * @param viewModel BoardViewModel
         */
        public ProductAdapter(BoardViewModel viewModel) {
            super(viewModel);
        }

        @Override
        public BaseDataBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ProductHolder(ItemBoardProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        class ProductHolder extends BaseDataBindingHolder<ItemBoardProductBinding, String> {

            /**
             * 상품 홀더
             *
             * @param binding ItemBoardProductBinding
             */
            public ProductHolder(ItemBoardProductBinding binding) {
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
