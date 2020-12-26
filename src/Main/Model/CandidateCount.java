package Main.Model;

public class CandidateCount
{
    private String Candidate_Name;
    private int Candidate_Count;

    public CandidateCount(String candidate_Name, int candidate_Count)
    {
        Candidate_Name = candidate_Name;
        Candidate_Count = candidate_Count;
    }

    public String getCandidate_Name()
    {
        return Candidate_Name;
    }

    public void setCandidate_Name(String candidate_Name)
    {
        Candidate_Name = candidate_Name;
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
