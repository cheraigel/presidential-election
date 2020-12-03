package Main.Model;

public class Candidate
{
    private String Candidate_Id,Candidate_Name;

    public Candidate(String candidate_Id, String candidate_Name)
    {
        Candidate_Id = candidate_Id;
        Candidate_Name = candidate_Name;
    }

    public String getCandidate_Id()
    {
        return Candidate_Id;
    }

    public void setCandidate_Id(String candidate_Id)
    {
        Candidate_Id = candidate_Id;
    }

    public String getCandidate_Name()
    {
        return Candidate_Name;
    }

    public void setCandidate_Name(String candidate_Name)
    {
        Candidate_Name = candidate_Name;
    }
}
