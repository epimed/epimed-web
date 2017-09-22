package epimed_web.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.Expression;


public interface ExpressionRepository extends MongoRepository<Expression, String>, ExpressionRepositoryCustom {

	public Expression findById(String idExpression);
}
