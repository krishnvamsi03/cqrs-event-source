package com.cqrs.account.query;

import com.cqrs.account.query.api.queries.*;
import com.cqrs.cqrs.core.queries.QueryDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QueryApplication {

	@Autowired
	private QueryDispatcher queryDispatcher;

	@Autowired
	private QueryHandler queryHandler;

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		queryDispatcher.registerHandle(FindByIdQuery.class, queryHandler::handle);
		queryDispatcher.registerHandle(FindAllAccountsQuery.class, queryHandler::handle);
		queryDispatcher.registerHandle(FindByAccountHolderQuery.class, queryHandler::handle);
		queryDispatcher.registerHandle(FindAccountByBalance.class, queryHandler::handle);

	}

}
