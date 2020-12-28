package Main.Model;

//class for ballot model
public class Ballot
{
    private String Ballot_Id,Ballot_Name;

    public Ballot(String ballot_Id, String ballot_Name)
    {
        Ballot_Id = ballot_Id;
        Ballot_Name = ballot_Name;
    }

    public String getBallot_Id()
    {
        return Ballot_Id;
    }

    public void setBallot_Id(String ballot_Id)
    {
        Ballot_Id = ballot_Id;
    }

    public String getBallot_Name()
    {
        return Ballot_Name;
    }

    public void setBallot_Name(String ballot_Name)
    {
        Ballot_Name = ballot_Name;
    }
}
