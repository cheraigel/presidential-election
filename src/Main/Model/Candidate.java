package Main.Model;

//class for candidate model
public class Candidate
{
    private String Id,Name,NIC,Party_Id,Party_Name,Address,TelNo;
    private int Age;

    public Candidate(String id, String name, String nic, String party_Id, String party_Name, String address, String telNo, int age) {
        Id = id;
        Name = name;
        NIC = nic;
        Party_Id = party_Id;
        Party_Name = party_Name;
        Address = address;
        TelNo = telNo;
        Age = age;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String nic) {
        NIC = nic;
    }

    public String getParty_Id() {
        return Party_Id;
    }

    public void setParty_Id(String party_Id) {
        Party_Id = party_Id;
    }

    public String getParty_Name() {
        return Party_Name;
    }

    public void setParty_Name(String party_Name) {
        Party_Name = party_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTelNo() {
        return TelNo;
    }

    public void setTelNo(String telNo) {
        TelNo = telNo;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }
}
