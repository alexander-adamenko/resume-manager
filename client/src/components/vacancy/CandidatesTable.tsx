import {Candidate} from "../../models/Candidate";
import {LoginDetails} from "../../models/AccountDetails";
import React, {useState} from "react";
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import 'react-bootstrap-table2-filter/dist/react-bootstrap-table2-filter.min.css';
import BootstrapTable, {SelectRowProps} from 'react-bootstrap-table-next';
import filterFactory, { textFilter } from 'react-bootstrap-table2-filter';
import {Button} from "react-bootstrap";
interface Props {
    candidates: Candidate[];
}

interface Pro extends SelectRowProps<Pro>{
    mode: 'checkbox';
    id: number;
}

function CandidatesTable (props: Props) {
    const [ids, setIds] = useState<number[]>([]);

    const selectRow: Pro = {
        mode: 'checkbox',
        id: 0,
        onSelect: (row, isSelect, rowIndex, e) => {
            if (isSelect) {
                // setIds(ids.)
                ids.push(row.id);
            }
            else {
                setIds(ids.splice(ids.indexOf(row.id), 1));
            }
            console.log(ids)
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
        filter: textFilter()
    }];
    return (
        <>
            <BootstrapTable selectRow={selectRow} keyField='id'  data={ props.candidates } columns={ columns } filter={ filterFactory() } />
            <Button
            onClick={()=> {
                console.log(ids);
            }}
            >
                qwe
            </Button>
            {ids.map((value, index) => {
                return (
                    <>
                        <p key={index}>
                            {value}
                        </p>
                    </>
                )
            })}
        </>
    );
}

export default CandidatesTable;