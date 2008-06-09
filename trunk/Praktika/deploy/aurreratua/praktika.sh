praktika_Lib="./lib"
jmagick_jni="/usr/lib/jni"

export LD_LIBRARY_PATH=$praktika_Lib:$jmagick_jni:$LD_LIBRARY_PATH

java -jar praktika.jar $1 $2 $3
