
/* JenkinsStatus will turn the LED at pin 13 on/off depending
 * on the value serially read.
 */
const int BUILD_SUCCESS = 46;
int inByte;

void setup() {
  Serial.begin(9600);
  pinMode(13, OUTPUT);
}

void loop() {
  if (Serial.available() > 0) {
      inByte = Serial.read();
      if (inByte == BUILD_SUCCESS) {
    	  digitalWrite(13, LOW);
      } else{
    	  digitalWrite(13, HIGH);
      }
  }
}

