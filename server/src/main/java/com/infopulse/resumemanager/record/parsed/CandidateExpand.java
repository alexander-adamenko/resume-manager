package com.infopulse.resumemanager.record.parsed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CandidateExpand {
        private String email;
        private String phone;
        private String education;
        private String aboutMe;
        private String filePath;
        private List<Map<String, String>> skills;
}
