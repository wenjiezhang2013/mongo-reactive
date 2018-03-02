package jz.demo.mongoreactive;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Component
public class CustomErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler
{
	CustomErrorWebExceptionHandler (ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
		super(new DefaultErrorAttributes(), new ResourceProperties(), applicationContext);
		this.setMessageWriters(serverCodecConfigurer.getWriters());
		this.setMessageReaders(serverCodecConfigurer.getReaders());
	}

	@Override
		protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
			return RouterFunctions.route((request) -> getError(request) instanceof IllegalAccessException, this::render4XX).andRoute(RequestPredicates.all(), this::render5XX);
		}


	protected Mono<ServerResponse> render4XX(ServerRequest serverRequest) {
		return ServerResponse.status(HttpStatus.BAD_GATEWAY)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(BodyInserters.fromObject("4xx"));
	}

	protected Mono<ServerResponse> render5XX(ServerRequest serverRequest) {
		return ServerResponse.status(HttpStatus.BAD_GATEWAY)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(BodyInserters.fromObject("5xx"));
	}

}