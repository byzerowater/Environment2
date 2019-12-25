package com.zerowater.environment.ui.login;

import com.zerowater.environment.R;
import com.zerowater.environment.databinding.FragmentLoginBinding;
import com.zerowater.environment.ui._base.BaseDaggerFragment;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import timber.log.Timber;


/**
 * Created by YoungSoo Kim on 2019-09-25.
 * Zero Ltd
 * byzerowater@gmail.com
 * LoginFragment
 */
public class LoginFragment extends BaseDaggerFragment<FragmentLoginBinding, LoginViewModel> {

    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
    }

    @Override
    protected Class getModelClass() {
        return LoginViewModel.class;
    }

    @Override
    protected void onActivityCreated(FragmentLoginBinding viewDataBinding, LoginViewModel viewModel) {
        viewDataBinding.setViewModel(viewModel);

        KeyboardVisibilityEvent.setEventListener(getActivity(), isOpen -> {
            viewModel.setOpenKeyboard(isOpen);
        });

        Timber.i("onActivityCreated");
    }


}
