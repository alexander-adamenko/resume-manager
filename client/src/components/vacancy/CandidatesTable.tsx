import {Candidate, CandidateWithProfile} from "../../models/Candidate";
import {LoginDetails} from "../../models/AccountDetails";
import React, {useState} from "react";
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import 'react-bootstrap-table2-filter/dist/react-bootstrap-table2-filter.min.css';
import BootstrapTable, {SelectRowProps} from 'react-bootstrap-table-next';
import filterFactory, {numberFilter, textFilter, TextFilterProps} from 'react-bootstrap-table2-filter';
import {Alert, Button, Spinner} from "react-bootstrap";
import {Vacancy} from "../../models/Vacancy";
import VacancyService from "../../services/VacancyService";
import placeholder from "lodash/fp/placeholder";
interface Props {
    candidates: CandidateWithProfile[];
    vacancy: Vacancy;
}

interface Pro extends SelectRowProps<Pro>{
    mode: 'checkbox';
    id: number;
}

function CandidatesTable (props: Props) {
    const [ids, setIds] = useState<number[]>([]);

    function sendMails () {
        setIsSending(true);
        VacancyService.sendMails({candidates: props.candidates.filter(value => ids.includes(value.id)), vacancy: props.vacancy}).then(response =>{
            if (response.status === 200) {
                setIsSending(false);
                toggleShow();
            }
        })
    }

    const [show, setShow] = useState(false);
    const [isSending, setIsSending] = useState(false);
    const toggleShow = () => setShow(!show);

    function priceFormatter(cell: any, row: any) {
        return (
            <a href={cell}>Link</a>
        );
    }


    const selectRow: Pro = {
        mode: 'checkbox',
        id: 0,
        onSelect: (row, isSelect, rowIndex, e) => {
            if (isSelect) {
                ids.push(row.id);
            }
            else {
                setIds([...ids.filter(value => value != row.id)]);
            }
        },
        onSelectAll:(isSelect, rows, e) => {
            if (isSelect) {
                setIds([...rows.map(value => value.id)]);
            }
            else {
                setIds([]);
            }
        }
    };
    const columns = [{
        dataField: 'name',
        text: 'Name'
    }, {
        dataField: 'degree',
        text: 'Degree',
        filter: textFilter()
    }, {
        dataField: 'englishLevel',
        text: 'English',
        filter: textFilter()
    }, {
        dataField: 'yearsOfExperience',
        text: 'Years',
        // @ts-ignore
        filter: numberFilter()
    }, {
        dataField: 'pathToProfile',
        text: 'Profile',
        formatter: priceFormatter
    }];
    return (
        <>
            <BootstrapTable
                noDataIndication="There is no candidates for such vacancy!"
                selectRow={selectRow}
                keyField='id'
                data={ props.candidates }
                columns={ columns }
                filter={ filterFactory() } />
            <Button
            onClick={()=> {
                sendMails();
            }}
            >
                Send Mails
            </Button>
            {isSending &&<Spinner style={{marginLeft: "100px"}} animation="border" variant="info" /> }
            {show ?
                (<Alert variant="success" onClose={() => setShow(false)} dismissible style={{marginTop: "70px"}}>
                    <Alert.Heading>All e-mails were sent!</Alert.Heading>
                </Alert>):(<></>)}
        </>
    );
}

export default CandidatesTable;