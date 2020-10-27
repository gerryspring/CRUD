package modelo;

public class Cliente {
    String RFC;
    String name;

    public Cliente(String RFC, String name, int age, int cityID) {
        this.RFC = RFC;
        this.name = name;
        this.age = age;
        this.cityID = cityID;
    }

    int age;
    int cityID;


    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }


}
