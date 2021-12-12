export interface Vacancy {
    id: number | undefined;
    positionTitle: string;
    isActive: boolean;
    minimumYearsOfExperience: number;
    degree: string;
    location: string;
    englishLevel: string;
    vacancySkills: VacancySkills[];
    description: string;
}

export interface VacancySkills {
    skill: Skill;
    level: string;
}

export class Skill {
    name: string;

    constructor(name: string) {
        this.name = name;
    }
}

export interface SkillsDegreesLevelsCitiesEnglishLevels {
    skills: Skill[];
    degrees: string[];
    levels: string[];
    cities: string[];
    englishLevels: string[];
}
