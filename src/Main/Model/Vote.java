package Main.Model;

public class Vote
{
    private String Ballot_ID,Candidate_Id;

    public Vote(String ballot_ID, String candidate_Id)
    {
        Ballot_ID = ballot_ID;
        Candidate_Id = candidate_Id;
    }

    public String getBallot_ID()
    {
        return Ballot_ID;
    }

    public void setBallot_ID(String ballot_ID)
    {
        Ballot_ID = ballot_ID;
    }

    public String getCandidate_Id()
    {
        return Candidate_Id;
    }

    public void setCandidate_Id(String candidate_Id)
    {
        Candidate_Id = candidate_Id;
    }
}
