.PHONY: install

install:
	source secret.sh && mvn clean install -DSkipTests:true

.PHONY: run

run:
	source secret.sh && mvn spring-boot:run