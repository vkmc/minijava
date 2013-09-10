#! /bin/bash
FAILURE=1
SUCCESS=0

CURRENT_DIR=`pwd`

for file in `ls`; do
	if [ "$file" != "SyntacticAnalyzer.jar" ] && [ "$file" != "run_tests.sh" ]; then
		echo "${CURRENT_DIR}/$file"
		java -jar SyntacticAnalyzer.jar "${CURRENT_DIR}/$file"
	fi
done

if [ $? -ne 0 ]
then
	echo "Tests failed"
	exit ${FAILURE}
fi

echo "Tests succeded"
exit ${SUCCESS}
