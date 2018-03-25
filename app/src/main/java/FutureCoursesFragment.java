import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.watshoulditake.waltermao.coursesapp.R;

public class FutureCoursesFragment extends Fragment {

    private static final String COURSE_CODE_ARG = "course_code";

    public static FutureCoursesFragment createFragment(String courseCode) {
        FutureCoursesFragment fragment = new FutureCoursesFragment();
        Bundle args = new Bundle();
        args.putString(COURSE_CODE_ARG,courseCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_future_courses,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
