package pl.piomin.services.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import pl.piomin.services.model.Account;
import pl.piomin.services.repository.AccountRepository;

import java.util.List;

@Service
public class AccountTools {

    private AccountRepository accountRepository;

    public AccountTools(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Tool(description = "Find all accounts by person ID")
    public List<Account> getAccountsByNationality(
            @ToolParam(description = "Person ID") Long personId) {
        return accountRepository.findByPersonId(personId);
    }
}
