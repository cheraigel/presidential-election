package Main.Model;

public class CandidateCount
{
    private String Candidate_Id;
    private int Candidate_Count;

    public CandidateCount(String candidate_Id, int candidate_Count)
    {
        Candidate_Id = candidate_Id;
        Candidate_Count = candidate_Count;
    }

    public String getCandidate_Name()
    {
        return Candidate_Id;
    }

    public void setCandidate_Name(String candidate_Name)
    {
        Candidate_Id = candidate_Name;
    }

    public int getCandidate_Count()
    {
        return Candidate_Count;
    }

    public void setCandidate_Count(int candidate_Count)
    {
        Candidate_Count = candidate_Count;
    }
}
