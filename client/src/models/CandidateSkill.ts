import {Skill} from "./Vacancy";

export class CandidateSkill{
    skill: Skill;
    level: string | undefined;

    constructor(skill: Skill, level: string | undefined) {
        this.skill = skill;
        this.level = level;
    }
}
