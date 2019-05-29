package kr.hs.dgsw.smartschool.dodamdodam.Model.offbase;

import android.os.Parcel;

public class Pass extends OffbaseItem {

    public Pass(Parcel in) {
        super(in);
    }

    public static final Creator<Pass> CREATOR = new Creator<Pass>() {
        @Override
        public Pass createFromParcel(Parcel in) {
            return new Pass(in);
        }

        @Override
        public Pass[] newArray(int size) {
            return new Pass[size];
        }
    };
}
