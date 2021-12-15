package com.infopulse.resumemanager.dto.parsed;

import com.infopulse.resumemanager.repository.entity.enums.City;
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
public class ExtendedCandidate {
        private String email;
        private String phone;
        private String education;
        private String aboutMe;
        private String filePath;
        private City city;
        private List<Map<String, String>> skills;
}
