package com.example.journal22.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TemplateViewModel extends AndroidViewModel {
    private TemplateRepo mRepository;

    private final LiveData<List<Template>> mAllTemplates;

    public TemplateViewModel (Application application) {
        super(application);
        mRepository = new TemplateRepo(application);
        mAllTemplates = mRepository.getAllTemplates();
    }

    public LiveData<List<Template>> getAllTemps() { return mAllTemplates; }

    public void insert(Template template) { mRepository.insert(template); }
    // public void delete(Entry entry) { mRepository.delete(entry); }
  //  public void deleteWord(Template entry) {mRepository.deleteWord(entry);}
    public Template getTemplate(int position) {return mRepository.getTemplateAtPosition(position);}

}
