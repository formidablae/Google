import os
import sys
from datetime import datetime
from posixpath import split


class Assignment:
    def __init__(self, project, contributer, role, skill, min_level, needs_mentoring):
        self.project = project
        self.contributer = contributer
        self.role = role
        self.skill = skill
        self.min_level = min_level
        self.needs_mentoring = needs_mentoring
    
    def to_string(self):
        return "ASSIGNMENT\nproject: " + self.project.name + "\ncontributer: " + self.contributer.name + "\nrole: " + str(self.role) + "\nskill: " + self.skill + "\nMin Level: " + str(self.min_level) + "\nNeeds Mentoring: " + str(self.needs_mentoring) + "\n"
        
class Mentoring:
    def __init__(self, teacher, student, skill, project, min_level):
        self.teacher = teacher
        self.student = student
        self.skill = skill
        self.project = project
        self.min_level = min_level
    
    def to_string(self):
        return "MENTORING\nTeacher: " + self.teacher + "\nStudent: " + self.student + "\nSkill: " + self.skill + "\nProject: " + self.project + "\nMin Level: " + self.min_level + "\n"


contributers_list = []
projects_set = set()
teachers_list = set()
all_skills_set = set()
assignments = {}
mentorings = {}

def count_projects():
    proj_set = set()
    for k, v in assignments.items():
        proj_set.add(k)
    return len(proj_set)

def did_improve(contributor, project, role, skill):
    for assignment in assignments:
        if assignment.project == project.name and assignment.contributor == contributor.name:
            if assignment.role == role:
                if assignment.skill == skill:
                    return True
    if contributor.skills[skill] < project.roles_skill_requirements[f"{role}_{skill}"]:
        return True
    return False

def improve(contributor, project, skill, new_level):
    contributor.skills[skill] = new_level


def assign(project, contributor, role, skill, min_level, needs_mentoring):
    if project.name not in assignments.keys():
        assignments[project.name] = [Assignment(project, contributor, role, skill, min_level, needs_mentoring)]
    else:
        assignments[project.name].append(Assignment(
            project, contributor, role, skill, min_level, needs_mentoring))


def mentor(teacher, student, skill, project, min_level):
    if teacher.name not in mentorings.keys():
        mentorings[teacher.name] = [Mentoring(teacher, student, skill, project, min_level)]
    else:
        mentorings[teacher.name].append(Mentoring(teacher, student, skill, project, min_level))

def can_mentor(self, teacher, student, skill, min_level):
    # Role 0 (requires Python level 3) is assigned to Anna (Python level 3).
    # ☑️ Anna has the same level in Python as required.
    # Role 1 (requires HTML level 1) is assigned to Bob(C++ level 3).
    # ⚠ Bob has level 0 in HTML. Since his level is only one below required, he can be assigned, but must be mentored by another contributor who knows HTML at level 1 or above.
    # Role 2 (requires CSS level 5) is assigned to Maria(HTML level 4, CSS level 6)
    # ☑️ Maria has a higher level than the one required for CSS.
    # ☑️ Maria can mentor Bob on HTML since she has HTML level 4.
    if mentorings[teacher.name].student.name == student.name:
        return 2  # already mentoring
    if teacher.skills_list[skill] >= min_level and student.skills_list[skill] - min_level == 1:
        return 1  # yes
    return 0  # no


    
def can_be_assigned_with_mentoring(project, contributor, role, skill, level):

    def can_be_assigned(project, contributor, level):
        if project.name in assignments.keys():
            for assign in assignments[project.name]:
                if contributor is assign.contributer:
                    return 0  # already assigned
        if skill in contributor.skills.keys() and contributor.skills[skill] >= level:
            return 2  # yes
        return 0  # no
    
    res = can_be_assigned(project, contributor, level)
    if res >= 2:
        return res
    for teacher in teachers_list:
        if can_mentor(teacher, contributor, skill, level) >= 1:
            return 1  # yes
    return 0  # no
    

class Contributor:
    def __init__(self, name, skills_dict):
        self.name = name
        self.skills = skills_dict
        for sk in all_skills_set:
            if sk not in self.skills.keys():
                self.skills[sk] = 0

    def __eq__(self, other):
        return self.__dict__ == other.__dict__

    def to_string(self):
        return "CONTRIBUTOR\nname: " + self.name + "\nSkills: " + str(self.skills) + "\n"

class Project:
    def __init__(self, name, duration_days, score, best_before_days, roles_skill_requirements):
        self.name = name
        self.duration_days = duration_days
        self.score = score
        self.best_before_days = best_before_days
        self.roles_skill_requirements = roles_skill_requirements
        for sk in all_skills_set:
            for n in range(len(self.roles_skill_requirements)):
                if f"{n}_{sk}" not in self.roles_skill_requirements.keys():
                    self.roles_skill_requirements[f"{n}_{sk}"] = 0
    
    def to_string(self):
        return "PROJECT\nname: " + self.name + "\nDuration: " + str(self.duration_days) + "\nScore: " + str(self.score) + "\nBest Before: " + str(self.best_before_days) + "\nRoles and Skills: " + str(self.roles_skill_requirements) + "\n"


def read_file(filename):
    with open("inputs/" + filename, 'r') as f:
        # first_line_str = f.readline()
        # map line to a, b, c
        c, p = [int(x) for x in f.readline().split()]
        
        for cc in range(c):
            line = f.readline().split(" ")
            contributer_name = line[0]
            count_skills = int(line[1])
            skills_dict = {}
            for n in range(count_skills):
                inner_line = f.readline().split(" ")
                skill_name = inner_line[0]
                skill_level = int(inner_line[1])
                skills_dict[skill_name] = skill_level
            contributers_list.append(Contributor(contributer_name, skills_dict))
        for pp in range(p):
            line = f.readline().split(" ")
            project_name = line[0]
            project_days = int(line[1])
            project_score = int(line[2])
            project_best_before = int(line[3])
            project_roles = int(line[4])
            roles_dict = {}
            for r in range(project_roles):
                inner_line = f.readline().split(" ")
                roles_dict[f"{r}_{inner_line[0]}"] = int(inner_line[1])
            projects_set.add(Project(project_name, project_days, project_score, project_best_before, roles_dict))


def write_file(data, filename):
    '''
    Write the output file
    '''
    with open("outputs/" + filename, 'w') as f:
        f.write(data)


if __name__ == '__main__':
    if len(sys.argv) < 2:
        sys.exit('Syntax: %s <filename>' % sys.argv[0])

    print('Running on file ', sys.argv[1])

    filename = sys.argv[1]
    read_file(filename)
    # for c in contributers_list:
    #     print(c.to_string())
    # for p in projects_set:
    #     print(p.to_string())
        
    for p in projects_set:
        for r in p.roles_skill_requirements.keys():
            key = r.split("_")
            role = key[0]
            skill = key[1]
            level = p.roles_skill_requirements[r]
            for c in contributers_list:
                can_be_assigned = can_be_assigned_with_mentoring(p, c, role, skill, level)
                if can_be_assigned >= 1:
                    assign(p, c, role, skill, level, can_be_assigned > 1)
                    break
                
    result_lines = []
    result_lines.append(str(count_projects()))
    for k, v in assignments.items():
        result_lines.append(k)
        people = []
        for assignment in v:
            people.append(assignment.contributer.name)
        result_lines.append(" ".join(people))
    result = "\n".join(result_lines)
    
    # do improvements
    
    # consider dates

    output_filename = filename.split(".")[0] + ".out.txt"
    write_file(result, output_filename)
        
    # datime now in %Y-%m-%d %H:%M:%S.%f format
    datetime_now = datetime.now().strftime("%Y-%m-%d_%H-%M-%S")
    folder_name = datetime.now().strftime("%Y-%m-%d_%H")
    # copy hc22.py to datetime in ISO format _hc22.py
    os.system(
        f'cp outputs/{output_filename} outputs/{folder_name}/{datetime_now}_{output_filename}')
    os.system(f'cp hc22.py versions/{datetime_now}_hc22.py')
