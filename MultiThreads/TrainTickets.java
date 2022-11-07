package MultiThreads;

public class TrainTickets {

    //this is a staticObj

    public int tickets;




    //const
    public TrainTickets(int tickets) {
        this.tickets = tickets;

    }


    synchronized public int getTickets() {
        return tickets;
    }


    //不同步的作法(會導致錯誤)
    public void setTickets(int tickets) {
        this.tickets = tickets;
    }
}
