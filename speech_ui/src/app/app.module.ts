import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { VoicedisplayComponent } from './components/voicedisplay/voicedisplay.component';
import {SafePipe} from './safe.pipe';
import { RecordVoiceComponent } from './components/record-voice/record-voice.component';
import { AudioRecordingService } from './components/record-voice/audio-recording.service';

@NgModule({
  declarations: [
    AppComponent,
    SafePipe,
    VoicedisplayComponent,
    RecordVoiceComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [AudioRecordingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
