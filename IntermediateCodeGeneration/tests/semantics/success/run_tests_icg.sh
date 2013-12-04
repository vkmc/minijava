#! /bin/bash
FAILURE=1
SUCCESS=0

CURRENT_DIR=`pwd`

rm *.txt

for file in `ls`; do
	if [ ${file: -5} == ".java" ]; then
		echo "${CURRENT_DIR}/$file"
                jar="${CURRENT_DIR}/$file"
                txt="${jar%.*}"
                txt="$txt.txt"
                #echo $jar
                #echo $txt
		java -jar IntermediateCodeGeneration.jar $jar $txt
                printf "\n\n"
	fi
done

if [ $? -ne 0 ]
then
	echo "Tests failed"
	exit ${FAILURE}
fi

echo "Tests succeded"
exit ${SUCCESS}
