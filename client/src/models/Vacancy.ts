export interface Vacancy {
    positionTitle: string;
    isActive: boolean;
    minimumYearsOfExperience: number;
    degree: string;
    location: string;
    vacancySkills: VacancySkills[];
    description: string;
}

export interface VacancySkills {
    skill: Skill;
    level: string;
}

export interface Skill {
    name: string;
}

export interface SkillsDegreesLevelsCities {
    skills: Skill[];
    degrees: string[];
    levels: string[];
    cities: string[];
}