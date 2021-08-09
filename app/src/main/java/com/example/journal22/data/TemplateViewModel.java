package com.example.journal22.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.journal22.data.entity.Template;

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
    public void deleteTemplate(Template template) {mRepository.deleteWord(template);}
    public void updateTemplate(Template template) {mRepository.updateWord(template);}

    public Template getTemplate(int position) {return mRepository.getTemplateAtPosition(position);}

}
