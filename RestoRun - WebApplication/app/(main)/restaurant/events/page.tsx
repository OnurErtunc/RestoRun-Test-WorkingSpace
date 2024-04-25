'use client';
import React, {useEffect, useState} from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Dialog } from 'primereact/dialog';
import { Calendar } from 'primereact/calendar';
import EventsAPI from "../../../api/restaurant-api/EventsAPI";

interface Event {
    id: string;
    title: string;
    description: string;
    startDate: string;
    endDate: string;
}

// @TODO ADD STATUS TO THE INTERFACE

const EventsPage = () => {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [date, setDate] = useState<Date>(new Date());

    const [events, setEvents] = useState<Event[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const [isEditMode, setIsEditMode] = useState(false);

    const fetchEvents = async () => {
        // fetch events from the server
        const api = new EventsAPI('http://localhost:8080');
        try {
            const data = await api.retrieveAllEvents();
            console.log("DEBUG");
            console.log(data);
            setEvents(data);
        }
        catch (err) {
            setError('Error fetching events');
            console.error(err);
        }
        finally {
            setLoading(false);
        }
    }
    useEffect(() => {
        fetchEvents();
    }, []);

    const handleModalToggle = () => {
        setIsModalVisible(!isModalVisible);
    };

    const convertStringToDate = (dateString: string): Date => {
        return new Date(dateString);
    }

    const convertDateToString = (date: Date): string => {
        return date.toISOString();
    }

    const renderModalContent = () => {
        return (
            <div>
                <Dialog header="Add New Event" visible={isModalVisible} style={{ width: '50vw' }} onHide={handleModalToggle}>
                    <div className="card p-fluid">
                        <div className="field">
                            <label htmlFor="title">Title of the Event</label>
                            <InputText id="title" type="text" value={title} onChange={(e) => setTitle(e.target.value)}/>
                        </div>
                        <div className="field">
                            <label htmlFor="description">Description</label>
                            <InputText id="description" type="description" value={description} onChange={(e) => setDescription(e.target.value)}/>
                        </div>
                        <div className="field">
                            <label htmlFor="startDate">Start Date</label>
                            <Calendar id="startDate" value={startDate} onChange={(e) => setStartDate(convertDateToString(e.value))}
                                      showTime/>
                        </div>
                        <div className="field">
                            <label htmlFor="endDate">End Date</label>
                            <Calendar id="endDate" value={endDate} onChange={(e) => setEndDate(convertDateToString(e.value))}
                                      showTime/>
                        </div>
                        <div className="field">
                            <Button label="Submit" className="p-button-success" size="small" icon="pi pi-check"
                                    onClick={handleSubmit}/>
                        </div>
                    </div>
                </Dialog>
            </div>
        );
    };

    const actionBodyTemplate = (rowData: Event) => {
        return (
            <React.Fragment>
                <Button icon="pi pi-check" className="p-button-success" size="small" onClick={() => handleEdit(rowData)} />
                <Button icon="pi pi-trash"  className="p-button-danger" size="small" style={{ marginLeft: '0.5em' }} onClick={() => handleDelete(rowData.id)}/>
            </React.Fragment>
        );
    };

    // Edit action handler
    const handleEdit = (rowData: Event) => {
        setTitle(rowData.title);
        setDescription(rowData.description);
        setStartDate(rowData.startDate);
        setEndDate(rowData.endDate);
        setIsModalVisible(true);
        setIsEditMode(true);
    };

    // Delete action handler
    const handleDelete = async (id: string) => {
        const api = new EventsAPI('http://localhost:8080');

        if (!id) {
            console.error('Error: No valid event ID provided for deletion.');
            return;
        }
        try {
            await api.deleteEvent(id);
            fetchEvents();
        }
        catch (err) {
            setError('Error deleting event');
            console.error(err);
        }
    };

    const handleSubmit = async () => {
        const api = new EventsAPI('http://localhost:8080');

        const event: Event = {
            id: '', // Assuming the ID is generated by the backend
            title: title,
            description: description,
            startDate: startDate,
            endDate: endDate,
        };

        try {
            await api.saveEvent(event);
            fetchEvents();
            setIsModalVisible(false);
        } catch (error) {
            setError('Failed to save event');
            console.error(error);
        }
    }
    return (
        <div className="grid">
            <div className="col-12">
                <div className="card">
                    <div className="col-6">
                        <h4>Events</h4>
                    </div>
                    <div className="col-6">
                        <Button label="Add New Event" className="p-button-success" size="small" icon="pi pi-plus" onClick={handleModalToggle} />
                    </div>
                    {renderModalContent()}
                    <DataTable value={events}>
                        <Column field="id" header="Event ID"></Column>
                        <Column field="title" header="Event Title"></Column>
                        <Column field="description" header="Event Description"></Column>
                        <Column field="startDate" header="Start Date"></Column>
                        <Column field="endDate" header="End Date"></Column>
                        <Column header="Actions" body={actionBodyTemplate} />
                    </DataTable>
                </div>
            </div>
        </div>
    );
};

export default EventsPage;