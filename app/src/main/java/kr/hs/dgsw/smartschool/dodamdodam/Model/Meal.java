package kr.hs.dgsw.smartschool.dodamdodam.Model;

import java.util.Date;
import java.util.Locale;

public class Meal {
    private Date date;
    private boolean exists;
    private String breakfast;
    private String lunch;
    private String dinner;
    private boolean isFilteredB = false;
    private boolean isFilteredL = false;
    private boolean isFilteredD = false;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getBreakfast() {
        if (!isFilteredB) {
            breakfast = filterMeal(breakfast);
            isFilteredB = !isFilteredB;
        }
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = filterMeal(breakfast);
    }

    public String getLunch() {
        if (!isFilteredL) {
            lunch = filterMeal(lunch);
            isFilteredL = !isFilteredL;
        }
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = filterMeal(lunch);
    }

    public String getDinner() {
        if (!isFilteredD) {
            dinner = filterMeal(dinner);
            isFilteredD = !isFilteredD;
        }
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = filterMeal(dinner);
    }

    private String filterMeal(String mealRaw) {
        String[] filters = {"[(]조[)]", "[(]중[)]", "[(]석[)]"};
        for (int i = 18; i >= 1; i--)
            mealRaw = mealRaw.replaceAll(String.format(Locale.getDefault(), "%d\\.", i), "");
        for (String filter : filters)
            mealRaw = mealRaw.replaceAll(filter, "");

         String[] lines = mealRaw.split("\n");

        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll(" $", "");
        }
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\d$|[(]$", "");
        }
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\d[+]", "+");
        }
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\d[(]", "(");
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (i == lines.length - 1)
                stringBuilder.append(line);
            else
                stringBuilder.append(line).append("\n");
        }

        return stringBuilder.toString();
    }
}
