package pl.jkanclerz.payment.payu;

import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class PayUTest {
    @Test
    public void itAllowsToRegisterPayment() {
        //Arrange
        var payu = thereIsPayU();
        var mySystemOrderId = UUID.randomUUID().toString();
        var exampleOrderCreateRequest = thereIsExampleOrderCreateRequest(mySystemOrderId);

        //Act
        OrderCreateResponse r = payu.handle(exampleOrderCreateRequest);

        //Assert
        assertThat(r.getExtOrderId()).isEqualTo(mySystemOrderId);
        assertThat(r.getRedirectUri()).isNotNull();
        assertThat(r.getOrderId()).isNotNull();
    }

    private OrderCreateRequest thereIsExampleOrderCreateRequest(String mySystemOrderId) {
        return OrderCreateRequest.builder()
                .notifyUrl("https://your.eshop.com/notify")
                .customerIp("127.0.0.1")
                .description("RTV market")
                .currencyCode("PLN")
                .totalAmount(21000)
                .extOrderId(mySystemOrderId)
                .buyer(Buyer.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .email("john.doe@example.com")
                        .language("pl")
                        .phone("654111654")
                        .build())
                .products(Arrays.asList(
                        new Product("Wireless Mouse for Laptop", 15000, 1),
                        new Product("Battery AAA", 1000, 2)
                ))
                .build();
    }

    private PayU thereIsPayU() {
        return new PayU();
    }
}