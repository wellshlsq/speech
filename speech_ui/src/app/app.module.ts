import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { VoicedisplayComponent } from './components/voicedisplay/voicedisplay.component';
import {SafePipe} from './safe.pipe';
import { RecordVoiceComponent } from './components/record-voice/record-voice.component';
import { AudioRecordingService } from './components/record-voice/audio-recording.service';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './components/login/login.component';
import { HeaderComponent } from './components/header/header.component';

@NgModule({
  declarations: [
    AppComponent,
    SafePipe,
    VoicedisplayComponent,
    RecordVoiceComponent,
    LoginComponent,
    HeaderComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [AudioRecordingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
