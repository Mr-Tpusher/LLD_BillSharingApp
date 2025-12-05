package dto.response;
import entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
@Builder
public class Transaction {
    private User payer;
    private User receiver;
    private Double amount;

    @Override
    public String toString() {
        return "{" +
                 payer.getUserName() + " ----- Rs." + amount  + " ------> " + receiver.getUserName()+
                '}';
    }
}
