package entity;

public class CandyEater
{
    private int MAX_EATING_NUM = 1;
    private String id;
    private int eatingNum;

    public int getEatingNum() {
        return eatingNum;
    }

    public void setEatingNum(int eatingNum) {
        this.eatingNum = eatingNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEating() {
        if (eatingNum == MAX_EATING_NUM) {
            return true;
        }
        return false;
    }

}
