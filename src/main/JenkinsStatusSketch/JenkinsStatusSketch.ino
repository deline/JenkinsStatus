
/* JenkinsStatus will turn the LED at pin 13 on/off depending
 * on the value serially read.
 */
const int BUILD_SUCCESS = 49;
int inByte;
int currentPinValue = 0;

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
  } else {
    currentPinValue = digitalRead(13);
    if (currentPinValue == HIGH) {
      delay(1000);
      digitalWrite(13, LOW);
      delay(1000);
      digitalWrite(13, HIGH);      
    }
  }
}

