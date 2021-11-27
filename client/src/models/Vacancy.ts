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

export class Skill {
    name: string;

    constructor(name: string) {
        this.name = name;
    }
}

export interface SkillsDegreesLevelsCities {
    skills: Skill[];
    degrees: string[];
    levels: string[];
    cities: string[];
}
