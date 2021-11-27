import {Skill} from "./Vacancy";

export class CandidateSkill{
    skillDto: Skill;
    level: string | undefined;

    constructor(skill: Skill, level: string | undefined) {
        this.skillDto = skill;
        this.level = level;
    }
}
